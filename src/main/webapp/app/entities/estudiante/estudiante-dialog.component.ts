import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Estudiante } from './estudiante.model';
import { EstudiantePopupService } from './estudiante-popup.service';
import { EstudianteService } from './estudiante.service';
import { User, UserService } from '../../shared';
import { Avatar, AvatarService } from '../avatar';
import { Curso, CursoService } from '../curso';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-estudiante-dialog',
    templateUrl: './estudiante-dialog.component.html'
})
export class EstudianteDialogComponent implements OnInit {

    estudiante: Estudiante;
    isSaving: boolean;

    users: User[];

    avatars: Avatar[];

    cursos: Curso[];
    fechaNacDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private estudianteService: EstudianteService,
        private userService: UserService,
        private avatarService: AvatarService,
        private cursoService: CursoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.avatarService
            .query({filter: 'estudiante-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.estudiante.avatar || !this.estudiante.avatar.id) {
                    this.avatars = res.json;
                } else {
                    this.avatarService
                        .find(this.estudiante.avatar.id)
                        .subscribe((subRes: Avatar) => {
                            this.avatars = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.cursoService.query()
            .subscribe((res: ResponseWrapper) => { this.cursos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.estudiante.id !== undefined) {
            this.subscribeToSaveResponse(
                this.estudianteService.update(this.estudiante));
        } else {
            this.subscribeToSaveResponse(
                this.estudianteService.create(this.estudiante));
        }
    }

    private subscribeToSaveResponse(result: Observable<Estudiante>) {
        result.subscribe((res: Estudiante) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Estudiante) {
        this.eventManager.broadcast({ name: 'estudianteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackAvatarById(index: number, item: Avatar) {
        return item.id;
    }

    trackCursoById(index: number, item: Curso) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-estudiante-popup',
    template: ''
})
export class EstudiantePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private estudiantePopupService: EstudiantePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.estudiantePopupService
                    .open(EstudianteDialogComponent as Component, params['id']);
            } else {
                this.estudiantePopupService
                    .open(EstudianteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
