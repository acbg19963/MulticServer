import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProfesorComponent } from './profesor.component';
import { ProfesorDetailComponent } from './profesor-detail.component';
import { ProfesorPopupComponent } from './profesor-dialog.component';
import { ProfesorDeletePopupComponent } from './profesor-delete-dialog.component';

export const profesorRoute: Routes = [
    {
        path: 'profesor',
        component: ProfesorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.profesor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'profesor/:id',
        component: ProfesorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.profesor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const profesorPopupRoute: Routes = [
    {
        path: 'profesor-new',
        component: ProfesorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.profesor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'profesor/:id/edit',
        component: ProfesorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.profesor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'profesor/:id/delete',
        component: ProfesorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'multicApp.profesor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
