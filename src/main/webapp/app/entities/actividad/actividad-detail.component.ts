import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Actividad } from './actividad.model';
import { ActividadService } from './actividad.service';

@Component({
    selector: 'jhi-actividad-detail',
    templateUrl: './actividad-detail.component.html'
})
export class ActividadDetailComponent implements OnInit, OnDestroy {

    actividad: Actividad;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private actividadService: ActividadService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInActividads();
    }

    load(id) {
        this.actividadService.find(id).subscribe((actividad) => {
            this.actividad = actividad;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInActividads() {
        this.eventSubscriber = this.eventManager.subscribe(
            'actividadListModification',
            (response) => this.load(this.actividad.id)
        );
    }
}
