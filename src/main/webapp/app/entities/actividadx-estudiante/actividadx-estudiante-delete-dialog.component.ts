import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ActividadxEstudiante } from './actividadx-estudiante.model';
import { ActividadxEstudiantePopupService } from './actividadx-estudiante-popup.service';
import { ActividadxEstudianteService } from './actividadx-estudiante.service';

@Component({
    selector: 'jhi-actividadx-estudiante-delete-dialog',
    templateUrl: './actividadx-estudiante-delete-dialog.component.html'
})
export class ActividadxEstudianteDeleteDialogComponent {

    actividadxEstudiante: ActividadxEstudiante;

    constructor(
        private actividadxEstudianteService: ActividadxEstudianteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.actividadxEstudianteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'actividadxEstudianteListModification',
                content: 'Deleted an actividadxEstudiante'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-actividadx-estudiante-delete-popup',
    template: ''
})
export class ActividadxEstudianteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private actividadxEstudiantePopupService: ActividadxEstudiantePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.actividadxEstudiantePopupService
                .open(ActividadxEstudianteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
