import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Actividad } from './actividad.model';
import { ActividadPopupService } from './actividad-popup.service';
import { ActividadService } from './actividad.service';

@Component({
    selector: 'jhi-actividad-delete-dialog',
    templateUrl: './actividad-delete-dialog.component.html'
})
export class ActividadDeleteDialogComponent {

    actividad: Actividad;

    constructor(
        private actividadService: ActividadService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.actividadService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'actividadListModification',
                content: 'Deleted an actividad'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-actividad-delete-popup',
    template: ''
})
export class ActividadDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private actividadPopupService: ActividadPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.actividadPopupService
                .open(ActividadDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
