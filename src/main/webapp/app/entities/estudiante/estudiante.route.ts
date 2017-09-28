import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EstudianteComponent } from './estudiante.component';
import { EstudianteDetailComponent } from './estudiante-detail.component';
import { EstudiantePopupComponent } from './estudiante-dialog.component';
import { EstudianteDeletePopupComponent } from './estudiante-delete-dialog.component';

export const estudianteRoute: Routes = [
    {
        path: 'estudiante',
        component: EstudianteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.estudiante.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'estudiante/:id',
        component: EstudianteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.estudiante.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const estudiantePopupRoute: Routes = [
    {
        path: 'estudiante-new',
        component: EstudiantePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.estudiante.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'estudiante/:id/edit',
        component: EstudiantePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.estudiante.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'estudiante/:id/delete',
        component: EstudianteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.estudiante.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
