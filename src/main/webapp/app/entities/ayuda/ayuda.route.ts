import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AyudaComponent } from './ayuda.component';
import { AyudaDetailComponent } from './ayuda-detail.component';
import { AyudaPopupComponent } from './ayuda-dialog.component';
import { AyudaDeletePopupComponent } from './ayuda-delete-dialog.component';

export const ayudaRoute: Routes = [
    {
        path: 'ayuda',
        component: AyudaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.ayuda.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ayuda/:id',
        component: AyudaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.ayuda.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ayudaPopupRoute: Routes = [
    {
        path: 'ayuda-new',
        component: AyudaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.ayuda.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ayuda/:id/edit',
        component: AyudaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.ayuda.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ayuda/:id/delete',
        component: AyudaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.ayuda.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
