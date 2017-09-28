import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CursoComponent } from './curso.component';
import { CursoDetailComponent } from './curso-detail.component';
import { CursoPopupComponent } from './curso-dialog.component';
import { CursoDeletePopupComponent } from './curso-delete-dialog.component';

export const cursoRoute: Routes = [
    {
        path: 'curso',
        component: CursoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.curso.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'curso/:id',
        component: CursoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.curso.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cursoPopupRoute: Routes = [
    {
        path: 'curso-new',
        component: CursoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.curso.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'curso/:id/edit',
        component: CursoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.curso.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'curso/:id/delete',
        component: CursoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.curso.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
