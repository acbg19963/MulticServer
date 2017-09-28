import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UniversoComponent } from './universo.component';
import { UniversoDetailComponent } from './universo-detail.component';
import { UniversoPopupComponent } from './universo-dialog.component';
import { UniversoDeletePopupComponent } from './universo-delete-dialog.component';

export const universoRoute: Routes = [
    {
        path: 'universo',
        component: UniversoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.universo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'universo/:id',
        component: UniversoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.universo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const universoPopupRoute: Routes = [
    {
        path: 'universo-new',
        component: UniversoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.universo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'universo/:id/edit',
        component: UniversoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.universo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'universo/:id/delete',
        component: UniversoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.universo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
