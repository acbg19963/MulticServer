import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ActividadxEstudiante } from './actividadx-estudiante.model';
import { ActividadxEstudianteService } from './actividadx-estudiante.service';

@Injectable()
export class ActividadxEstudiantePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private actividadxEstudianteService: ActividadxEstudianteService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.actividadxEstudianteService.find(id).subscribe((actividadxEstudiante) => {
                    this.ngbModalRef = this.actividadxEstudianteModalRef(component, actividadxEstudiante);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.actividadxEstudianteModalRef(component, new ActividadxEstudiante());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    actividadxEstudianteModalRef(component: Component, actividadxEstudiante: ActividadxEstudiante): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.actividadxEstudiante = actividadxEstudiante;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
