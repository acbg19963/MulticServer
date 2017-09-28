import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Ayuda } from './ayuda.model';
import { AyudaService } from './ayuda.service';

@Component({
    selector: 'jhi-ayuda-detail',
    templateUrl: './ayuda-detail.component.html'
})
export class AyudaDetailComponent implements OnInit, OnDestroy {

    ayuda: Ayuda;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ayudaService: AyudaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAyudas();
    }

    load(id) {
        this.ayudaService.find(id).subscribe((ayuda) => {
            this.ayuda = ayuda;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAyudas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ayudaListModification',
            (response) => this.load(this.ayuda.id)
        );
    }
}
