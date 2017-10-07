import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ActividadxEstudiante } from './actividadx-estudiante.model';
import { ActividadxEstudiantePopupService } from './actividadx-estudiante-popup.service';
import { ActividadxEstudianteService } from './actividadx-estudiante.service';
import { Estudiante, EstudianteService } from '../estudiante';
import { Actividad, ActividadService } from '../actividad';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-actividadx-estudiante-dialog',
    templateUrl: './actividadx-estudiante-dialog.component.html'
})
export class ActividadxEstudianteDialogComponent implements OnInit {

    actividadxEstudiante: ActividadxEstudiante;
    isSaving: boolean;

    estudiantes: Estudiante[];

    actividads: Actividad[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private actividadxEstudianteService: ActividadxEstudianteService,
        private estudianteService: EstudianteService,
        private actividadService: ActividadService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.estudianteService.query()
            .subscribe((res: ResponseWrapper) => { this.estudiantes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.actividadService.query()
            .subscribe((res: ResponseWrapper) => { this.actividads = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.actividadxEstudiante.id !== undefined) {
            this.subscribeToSaveResponse(
                this.actividadxEstudianteService.update(this.actividadxEstudiante));
        } else {
            this.subscribeToSaveResponse(
                this.actividadxEstudianteService.create(this.actividadxEstudiante));
        }
    }

    private subscribeToSaveResponse(result: Observable<ActividadxEstudiante>) {
        result.subscribe((res: ActividadxEstudiante) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ActividadxEstudiante) {
        this.eventManager.broadcast({ name: 'actividadxEstudianteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackEstudianteById(index: number, item: Estudiante) {
        return item.id;
    }

    trackActividadById(index: number, item: Actividad) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-actividadx-estudiante-popup',
    template: ''
})
export class ActividadxEstudiantePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private actividadxEstudiantePopupService: ActividadxEstudiantePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.actividadxEstudiantePopupService
                    .open(ActividadxEstudianteDialogComponent as Component, params['id']);
            } else {
                this.actividadxEstudiantePopupService
                    .open(ActividadxEstudianteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
