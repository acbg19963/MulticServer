import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Universo } from './universo.model';
import { UniversoPopupService } from './universo-popup.service';
import { UniversoService } from './universo.service';

@Component({
    selector: 'jhi-universo-delete-dialog',
    templateUrl: './universo-delete-dialog.component.html'
})
export class UniversoDeleteDialogComponent {

    universo: Universo;

    constructor(
        private universoService: UniversoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.universoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'universoListModification',
                content: 'Deleted an universo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-universo-delete-popup',
    template: ''
})
export class UniversoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private universoPopupService: UniversoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.universoPopupService
                .open(UniversoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
