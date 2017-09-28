import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TipEstudiante } from './tip-estudiante.model';
import { TipEstudianteService } from './tip-estudiante.service';

@Component({
    selector: 'jhi-tip-estudiante-detail',
    templateUrl: './tip-estudiante-detail.component.html'
})
export class TipEstudianteDetailComponent implements OnInit, OnDestroy {

    tipEstudiante: TipEstudiante;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tipEstudianteService: TipEstudianteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTipEstudiantes();
    }

    load(id) {
        this.tipEstudianteService.find(id).subscribe((tipEstudiante) => {
            this.tipEstudiante = tipEstudiante;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTipEstudiantes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tipEstudianteListModification',
            (response) => this.load(this.tipEstudiante.id)
        );
    }
}
