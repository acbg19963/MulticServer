import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Curso } from './curso.model';
import { CursoPopupService } from './curso-popup.service';
import { CursoService } from './curso.service';

@Component({
    selector: 'jhi-curso-delete-dialog',
    templateUrl: './curso-delete-dialog.component.html'
})
export class CursoDeleteDialogComponent {

    curso: Curso;

    constructor(
        private cursoService: CursoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cursoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cursoListModification',
                content: 'Deleted an curso'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-curso-delete-popup',
    template: ''
})
export class CursoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cursoPopupService: CursoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cursoPopupService
                .open(CursoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
