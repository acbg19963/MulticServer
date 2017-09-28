import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Profesor } from './profesor.model';
import { ProfesorPopupService } from './profesor-popup.service';
import { ProfesorService } from './profesor.service';

@Component({
    selector: 'jhi-profesor-delete-dialog',
    templateUrl: './profesor-delete-dialog.component.html'
})
export class ProfesorDeleteDialogComponent {

    profesor: Profesor;

    constructor(
        private profesorService: ProfesorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.profesorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'profesorListModification',
                content: 'Deleted an profesor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-profesor-delete-popup',
    template: ''
})
export class ProfesorDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private profesorPopupService: ProfesorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.profesorPopupService
                .open(ProfesorDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
