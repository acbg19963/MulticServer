import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Estudiante } from './estudiante.model';
import { EstudiantePopupService } from './estudiante-popup.service';
import { EstudianteService } from './estudiante.service';

@Component({
    selector: 'jhi-estudiante-delete-dialog',
    templateUrl: './estudiante-delete-dialog.component.html'
})
export class EstudianteDeleteDialogComponent {

    estudiante: Estudiante;

    constructor(
        private estudianteService: EstudianteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.estudianteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'estudianteListModification',
                content: 'Deleted an estudiante'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-estudiante-delete-popup',
    template: ''
})
export class EstudianteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private estudiantePopupService: EstudiantePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.estudiantePopupService
                .open(EstudianteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
