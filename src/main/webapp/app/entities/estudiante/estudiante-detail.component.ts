import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Estudiante } from './estudiante.model';
import { EstudianteService } from './estudiante.service';

@Component({
    selector: 'jhi-estudiante-detail',
    templateUrl: './estudiante-detail.component.html'
})
export class EstudianteDetailComponent implements OnInit, OnDestroy {

    estudiante: Estudiante;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private estudianteService: EstudianteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEstudiantes();
    }

    load(id) {
        this.estudianteService.find(id).subscribe((estudiante) => {
            this.estudiante = estudiante;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEstudiantes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'estudianteListModification',
            (response) => this.load(this.estudiante.id)
        );
    }
}
