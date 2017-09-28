import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Universo } from './universo.model';
import { UniversoService } from './universo.service';

@Component({
    selector: 'jhi-universo-detail',
    templateUrl: './universo-detail.component.html'
})
export class UniversoDetailComponent implements OnInit, OnDestroy {

    universo: Universo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private universoService: UniversoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUniversos();
    }

    load(id) {
        this.universoService.find(id).subscribe((universo) => {
            this.universo = universo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUniversos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'universoListModification',
            (response) => this.load(this.universo.id)
        );
    }
}
