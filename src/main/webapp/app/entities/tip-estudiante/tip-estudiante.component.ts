import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { TipEstudiante } from './tip-estudiante.model';
import { TipEstudianteService } from './tip-estudiante.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-tip-estudiante',
    templateUrl: './tip-estudiante.component.html'
})
export class TipEstudianteComponent implements OnInit, OnDestroy {
tipEstudiantes: TipEstudiante[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tipEstudianteService: TipEstudianteService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tipEstudianteService.query().subscribe(
            (res: ResponseWrapper) => {
                this.tipEstudiantes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTipEstudiantes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TipEstudiante) {
        return item.id;
    }
    registerChangeInTipEstudiantes() {
        this.eventSubscriber = this.eventManager.subscribe('tipEstudianteListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
