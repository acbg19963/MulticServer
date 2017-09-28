import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ayuda } from './ayuda.model';
import { AyudaPopupService } from './ayuda-popup.service';
import { AyudaService } from './ayuda.service';
import { Actividad, ActividadService } from '../actividad';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ayuda-dialog',
    templateUrl: './ayuda-dialog.component.html'
})
export class AyudaDialogComponent implements OnInit {

    ayuda: Ayuda;
    isSaving: boolean;

    actividads: Actividad[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private ayudaService: AyudaService,
        private actividadService: ActividadService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.actividadService.query()
            .subscribe((res: ResponseWrapper) => { this.actividads = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ayuda.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ayudaService.update(this.ayuda));
        } else {
            this.subscribeToSaveResponse(
                this.ayudaService.create(this.ayuda));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ayuda>) {
        result.subscribe((res: Ayuda) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Ayuda) {
        this.eventManager.broadcast({ name: 'ayudaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackActividadById(index: number, item: Actividad) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ayuda-popup',
    template: ''
})
export class AyudaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ayudaPopupService: AyudaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ayudaPopupService
                    .open(AyudaDialogComponent as Component, params['id']);
            } else {
                this.ayudaPopupService
                    .open(AyudaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
