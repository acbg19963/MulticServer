import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ayuda } from './ayuda.model';
import { AyudaPopupService } from './ayuda-popup.service';
import { AyudaService } from './ayuda.service';

@Component({
    selector: 'jhi-ayuda-delete-dialog',
    templateUrl: './ayuda-delete-dialog.component.html'
})
export class AyudaDeleteDialogComponent {

    ayuda: Ayuda;

    constructor(
        private ayudaService: AyudaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ayudaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ayudaListModification',
                content: 'Deleted an ayuda'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ayuda-delete-popup',
    template: ''
})
export class AyudaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ayudaPopupService: AyudaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ayudaPopupService
                .open(AyudaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
