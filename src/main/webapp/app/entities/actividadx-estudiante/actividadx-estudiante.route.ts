import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ActividadxEstudianteComponent } from './actividadx-estudiante.component';
import { ActividadxEstudianteDetailComponent } from './actividadx-estudiante-detail.component';
import { ActividadxEstudiantePopupComponent } from './actividadx-estudiante-dialog.component';
import { ActividadxEstudianteDeletePopupComponent } from './actividadx-estudiante-delete-dialog.component';

export const actividadxEstudianteRoute: Routes = [
    {
        path: 'actividadx-estudiante',
        component: ActividadxEstudianteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.actividadxEstudiante.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'actividadx-estudiante/:id',
        component: ActividadxEstudianteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.actividadxEstudiante.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actividadxEstudiantePopupRoute: Routes = [
    {
        path: 'actividadx-estudiante-new',
        component: ActividadxEstudiantePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.actividadxEstudiante.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'actividadx-estudiante/:id/edit',
        component: ActividadxEstudiantePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.actividadxEstudiante.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'actividadx-estudiante/:id/delete',
        component: ActividadxEstudianteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.actividadxEstudiante.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
