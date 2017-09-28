import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MulticSharedModule } from '../../shared';
import {
    RespuestaService,
    RespuestaPopupService,
    RespuestaComponent,
    RespuestaDetailComponent,
    RespuestaDialogComponent,
    RespuestaPopupComponent,
    RespuestaDeletePopupComponent,
    RespuestaDeleteDialogComponent,
    respuestaRoute,
    respuestaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...respuestaRoute,
    ...respuestaPopupRoute,
];

@NgModule({
    imports: [
        MulticSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RespuestaComponent,
        RespuestaDetailComponent,
        RespuestaDialogComponent,
        RespuestaDeleteDialogComponent,
        RespuestaPopupComponent,
        RespuestaDeletePopupComponent,
    ],
    entryComponents: [
        RespuestaComponent,
        RespuestaDialogComponent,
        RespuestaPopupComponent,
        RespuestaDeleteDialogComponent,
        RespuestaDeletePopupComponent,
    ],
    providers: [
        RespuestaService,
        RespuestaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MulticRespuestaModule {}
