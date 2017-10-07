import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ActividadxEstudiante } from './actividadx-estudiante.model';
import { ActividadxEstudianteService } from './actividadx-estudiante.service';

@Component({
    selector: 'jhi-actividadx-estudiante-detail',
    templateUrl: './actividadx-estudiante-detail.component.html'
})
export class ActividadxEstudianteDetailComponent implements OnInit, OnDestroy {

    actividadxEstudiante: ActividadxEstudiante;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private actividadxEstudianteService: ActividadxEstudianteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInActividadxEstudiantes();
    }

    load(id) {
        this.actividadxEstudianteService.find(id).subscribe((actividadxEstudiante) => {
            this.actividadxEstudiante = actividadxEstudiante;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInActividadxEstudiantes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'actividadxEstudianteListModification',
            (response) => this.load(this.actividadxEstudiante.id)
        );
    }
}
