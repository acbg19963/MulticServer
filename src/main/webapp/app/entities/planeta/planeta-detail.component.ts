import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Planeta } from './planeta.model';
import { PlanetaService } from './planeta.service';

@Component({
    selector: 'jhi-planeta-detail',
    templateUrl: './planeta-detail.component.html'
})
export class PlanetaDetailComponent implements OnInit, OnDestroy {

    planeta: Planeta;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private planetaService: PlanetaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlanetas();
    }

    load(id) {
        this.planetaService.find(id).subscribe((planeta) => {
            this.planeta = planeta;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlanetas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'planetaListModification',
            (response) => this.load(this.planeta.id)
        );
    }
}
