import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Curso } from './curso.model';
import { CursoService } from './curso.service';

@Component({
    selector: 'jhi-curso-detail',
    templateUrl: './curso-detail.component.html'
})
export class CursoDetailComponent implements OnInit, OnDestroy {

    curso: Curso;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cursoService: CursoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCursos();
    }

    load(id) {
        this.cursoService.find(id).subscribe((curso) => {
            this.curso = curso;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCursos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cursoListModification',
            (response) => this.load(this.curso.id)
        );
    }
}
