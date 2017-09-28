import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Planeta } from './planeta.model';
import { PlanetaPopupService } from './planeta-popup.service';
import { PlanetaService } from './planeta.service';
import { Universo, UniversoService } from '../universo';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-planeta-dialog',
    templateUrl: './planeta-dialog.component.html'
})
export class PlanetaDialogComponent implements OnInit {

    planeta: Planeta;
    isSaving: boolean;

    universos: Universo[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private planetaService: PlanetaService,
        private universoService: UniversoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.universoService.query()
            .subscribe((res: ResponseWrapper) => { this.universos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.planeta.id !== undefined) {
            this.subscribeToSaveResponse(
                this.planetaService.update(this.planeta));
        } else {
            this.subscribeToSaveResponse(
                this.planetaService.create(this.planeta));
        }
    }

    private subscribeToSaveResponse(result: Observable<Planeta>) {
        result.subscribe((res: Planeta) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Planeta) {
        this.eventManager.broadcast({ name: 'planetaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackUniversoById(index: number, item: Universo) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-planeta-popup',
    template: ''
})
export class PlanetaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private planetaPopupService: PlanetaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.planetaPopupService
                    .open(PlanetaDialogComponent as Component, params['id']);
            } else {
                this.planetaPopupService
                    .open(PlanetaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
