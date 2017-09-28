import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TipEstudiante } from './tip-estudiante.model';
import { TipEstudiantePopupService } from './tip-estudiante-popup.service';
import { TipEstudianteService } from './tip-estudiante.service';

@Component({
    selector: 'jhi-tip-estudiante-delete-dialog',
    templateUrl: './tip-estudiante-delete-dialog.component.html'
})
export class TipEstudianteDeleteDialogComponent {

    tipEstudiante: TipEstudiante;

    constructor(
        private tipEstudianteService: TipEstudianteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipEstudianteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tipEstudianteListModification',
                content: 'Deleted an tipEstudiante'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tip-estudiante-delete-popup',
    template: ''
})
export class TipEstudianteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipEstudiantePopupService: TipEstudiantePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tipEstudiantePopupService
                .open(TipEstudianteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
