import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Estudiante } from './estudiante.model';
import { EstudianteService } from './estudiante.service';

@Injectable()
export class EstudiantePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private estudianteService: EstudianteService

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
                this.estudianteService.find(id).subscribe((estudiante) => {
                    if (estudiante.fechaNac) {
                        estudiante.fechaNac = {
                            year: estudiante.fechaNac.getFullYear(),
                            month: estudiante.fechaNac.getMonth() + 1,
                            day: estudiante.fechaNac.getDate()
                        };
                    }
                    this.ngbModalRef = this.estudianteModalRef(component, estudiante);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.estudianteModalRef(component, new Estudiante());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    estudianteModalRef(component: Component, estudiante: Estudiante): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.estudiante = estudiante;
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
