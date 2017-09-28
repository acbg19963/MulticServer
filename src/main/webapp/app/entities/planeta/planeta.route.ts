import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PlanetaComponent } from './planeta.component';
import { PlanetaDetailComponent } from './planeta-detail.component';
import { PlanetaPopupComponent } from './planeta-dialog.component';
import { PlanetaDeletePopupComponent } from './planeta-delete-dialog.component';

export const planetaRoute: Routes = [
    {
        path: 'planeta',
        component: PlanetaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.planeta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'planeta/:id',
        component: PlanetaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.planeta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const planetaPopupRoute: Routes = [
    {
        path: 'planeta-new',
        component: PlanetaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.planeta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'planeta/:id/edit',
        component: PlanetaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.planeta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'planeta/:id/delete',
        component: PlanetaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.planeta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
