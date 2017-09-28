import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ActividadComponent } from './actividad.component';
import { ActividadDetailComponent } from './actividad-detail.component';
import { ActividadPopupComponent } from './actividad-dialog.component';
import { ActividadDeletePopupComponent } from './actividad-delete-dialog.component';

export const actividadRoute: Routes = [
    {
        path: 'actividad',
        component: ActividadComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.actividad.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'actividad/:id',
        component: ActividadDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.actividad.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actividadPopupRoute: Routes = [
    {
        path: 'actividad-new',
        component: ActividadPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.actividad.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'actividad/:id/edit',
        component: ActividadPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.actividad.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'actividad/:id/delete',
        component: ActividadDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.actividad.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
