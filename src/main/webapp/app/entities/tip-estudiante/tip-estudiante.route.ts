import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TipEstudianteComponent } from './tip-estudiante.component';
import { TipEstudianteDetailComponent } from './tip-estudiante-detail.component';
import { TipEstudiantePopupComponent } from './tip-estudiante-dialog.component';
import { TipEstudianteDeletePopupComponent } from './tip-estudiante-delete-dialog.component';

export const tipEstudianteRoute: Routes = [
    {
        path: 'tip-estudiante',
        component: TipEstudianteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.tipEstudiante.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tip-estudiante/:id',
        component: TipEstudianteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.tipEstudiante.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipEstudiantePopupRoute: Routes = [
    {
        path: 'tip-estudiante-new',
        component: TipEstudiantePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.tipEstudiante.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tip-estudiante/:id/edit',
        component: TipEstudiantePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.tipEstudiante.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tip-estudiante/:id/delete',
        component: TipEstudianteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.tipEstudiante.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
