import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Actividad } from './actividad.model';
import { ActividadPopupService } from './actividad-popup.service';
import { ActividadService } from './actividad.service';
import { Planeta, PlanetaService } from '../planeta';
import { Profesor, ProfesorService } from '../profesor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-actividad-dialog',
    templateUrl: './actividad-dialog.component.html'
})
export class ActividadDialogComponent implements OnInit {

    actividad: Actividad;
    isSaving: boolean;

    planetas: Planeta[];

    profesors: Profesor[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private actividadService: ActividadService,
        private planetaService: PlanetaService,
        private profesorService: ProfesorService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.planetaService.query()
            .subscribe((res: ResponseWrapper) => { this.planetas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.profesorService.query()
            .subscribe((res: ResponseWrapper) => { this.profesors = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.actividad.id !== undefined) {
            this.subscribeToSaveResponse(
                this.actividadService.update(this.actividad));
        } else {
            this.subscribeToSaveResponse(
                this.actividadService.create(this.actividad));
        }
    }

    private subscribeToSaveResponse(result: Observable<Actividad>) {
        result.subscribe((res: Actividad) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Actividad) {
        this.eventManager.broadcast({ name: 'actividadListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPlanetaById(index: number, item: Planeta) {
        return item.id;
    }

    trackProfesorById(index: number, item: Profesor) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-actividad-popup',
    template: ''
})
export class ActividadPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private actividadPopupService: ActividadPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.actividadPopupService
                    .open(ActividadDialogComponent as Component, params['id']);
            } else {
                this.actividadPopupService
                    .open(ActividadDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
