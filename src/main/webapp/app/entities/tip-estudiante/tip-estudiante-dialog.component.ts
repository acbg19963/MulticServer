import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TipEstudiante } from './tip-estudiante.model';
import { TipEstudiantePopupService } from './tip-estudiante-popup.service';
import { TipEstudianteService } from './tip-estudiante.service';
import { Estudiante, EstudianteService } from '../estudiante';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-tip-estudiante-dialog',
    templateUrl: './tip-estudiante-dialog.component.html'
})
export class TipEstudianteDialogComponent implements OnInit {

    tipEstudiante: TipEstudiante;
    isSaving: boolean;

    estudiantes: Estudiante[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private tipEstudianteService: TipEstudianteService,
        private estudianteService: EstudianteService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.estudianteService.query()
            .subscribe((res: ResponseWrapper) => { this.estudiantes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tipEstudiante.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tipEstudianteService.update(this.tipEstudiante));
        } else {
            this.subscribeToSaveResponse(
                this.tipEstudianteService.create(this.tipEstudiante));
        }
    }

    private subscribeToSaveResponse(result: Observable<TipEstudiante>) {
        result.subscribe((res: TipEstudiante) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TipEstudiante) {
        this.eventManager.broadcast({ name: 'tipEstudianteListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-tip-estudiante-popup',
    template: ''
})
export class TipEstudiantePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipEstudiantePopupService: TipEstudiantePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tipEstudiantePopupService
                    .open(TipEstudianteDialogComponent as Component, params['id']);
            } else {
                this.tipEstudiantePopupService
                    .open(TipEstudianteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
