import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Curso } from './curso.model';
import { CursoPopupService } from './curso-popup.service';
import { CursoService } from './curso.service';
import { Profesor, ProfesorService } from '../profesor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-curso-dialog',
    templateUrl: './curso-dialog.component.html'
})
export class CursoDialogComponent implements OnInit {

    curso: Curso;
    isSaving: boolean;

    profesors: Profesor[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private cursoService: CursoService,
        private profesorService: ProfesorService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.profesorService.query()
            .subscribe((res: ResponseWrapper) => { this.profesors = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.curso.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cursoService.update(this.curso));
        } else {
            this.subscribeToSaveResponse(
                this.cursoService.create(this.curso));
        }
    }

    private subscribeToSaveResponse(result: Observable<Curso>) {
        result.subscribe((res: Curso) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Curso) {
        this.eventManager.broadcast({ name: 'cursoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackProfesorById(index: number, item: Profesor) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-curso-popup',
    template: ''
})
export class CursoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cursoPopupService: CursoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cursoPopupService
                    .open(CursoDialogComponent as Component, params['id']);
            } else {
                this.cursoPopupService
                    .open(CursoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
