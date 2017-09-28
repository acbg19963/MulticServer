import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Profesor } from './profesor.model';
import { ProfesorService } from './profesor.service';

@Component({
    selector: 'jhi-profesor-detail',
    templateUrl: './profesor-detail.component.html'
})
export class ProfesorDetailComponent implements OnInit, OnDestroy {

    profesor: Profesor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private profesorService: ProfesorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProfesors();
    }

    load(id) {
        this.profesorService.find(id).subscribe((profesor) => {
            this.profesor = profesor;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProfesors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'profesorListModification',
            (response) => this.load(this.profesor.id)
        );
    }
}
