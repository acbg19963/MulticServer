import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Planeta } from './planeta.model';
import { PlanetaPopupService } from './planeta-popup.service';
import { PlanetaService } from './planeta.service';

@Component({
    selector: 'jhi-planeta-delete-dialog',
    templateUrl: './planeta-delete-dialog.component.html'
})
export class PlanetaDeleteDialogComponent {

    planeta: Planeta;

    constructor(
        private planetaService: PlanetaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.planetaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'planetaListModification',
                content: 'Deleted an planeta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-planeta-delete-popup',
    template: ''
})
export class PlanetaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private planetaPopupService: PlanetaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.planetaPopupService
                .open(PlanetaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
