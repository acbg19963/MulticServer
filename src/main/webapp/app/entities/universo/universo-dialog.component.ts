import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Universo } from './universo.model';
import { UniversoPopupService } from './universo-popup.service';
import { UniversoService } from './universo.service';

@Component({
    selector: 'jhi-universo-dialog',
    templateUrl: './universo-dialog.component.html'
})
export class UniversoDialogComponent implements OnInit {

    universo: Universo;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private universoService: UniversoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.universo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.universoService.update(this.universo));
        } else {
            this.subscribeToSaveResponse(
                this.universoService.create(this.universo));
        }
    }

    private subscribeToSaveResponse(result: Observable<Universo>) {
        result.subscribe((res: Universo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Universo) {
        this.eventManager.broadcast({ name: 'universoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-universo-popup',
    template: ''
})
export class UniversoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private universoPopupService: UniversoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.universoPopupService
                    .open(UniversoDialogComponent as Component, params['id']);
            } else {
                this.universoPopupService
                    .open(UniversoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
