import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Avatar } from './avatar.model';
import { AvatarPopupService } from './avatar-popup.service';
import { AvatarService } from './avatar.service';
import { Universo, UniversoService } from '../universo';
import { Estudiante, EstudianteService } from '../estudiante';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-avatar-dialog',
    templateUrl: './avatar-dialog.component.html'
})
export class AvatarDialogComponent implements OnInit {

    avatar: Avatar;
    isSaving: boolean;

    universos: Universo[];

    estudiantes: Estudiante[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private avatarService: AvatarService,
        private universoService: UniversoService,
        private estudianteService: EstudianteService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.universoService.query()
            .subscribe((res: ResponseWrapper) => { this.universos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.estudianteService.query()
            .subscribe((res: ResponseWrapper) => { this.estudiantes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.avatar.id !== undefined) {
            this.subscribeToSaveResponse(
                this.avatarService.update(this.avatar));
        } else {
            this.subscribeToSaveResponse(
                this.avatarService.create(this.avatar));
        }
    }

    private subscribeToSaveResponse(result: Observable<Avatar>) {
        result.subscribe((res: Avatar) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Avatar) {
        this.eventManager.broadcast({ name: 'avatarListModification', content: 'OK'});
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

    trackEstudianteById(index: number, item: Estudiante) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-avatar-popup',
    template: ''
})
export class AvatarPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private avatarPopupService: AvatarPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.avatarPopupService
                    .open(AvatarDialogComponent as Component, params['id']);
            } else {
                this.avatarPopupService
                    .open(AvatarDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
