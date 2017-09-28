import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MulticSharedModule } from '../../shared';
import {
    UniversoService,
    UniversoPopupService,
    UniversoComponent,
    UniversoDetailComponent,
    UniversoDialogComponent,
    UniversoPopupComponent,
    UniversoDeletePopupComponent,
    UniversoDeleteDialogComponent,
    universoRoute,
    universoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...universoRoute,
    ...universoPopupRoute,
];

@NgModule({
    imports: [
        MulticSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UniversoComponent,
        UniversoDetailComponent,
        UniversoDialogComponent,
        UniversoDeleteDialogComponent,
        UniversoPopupComponent,
        UniversoDeletePopupComponent,
    ],
    entryComponents: [
        UniversoComponent,
        UniversoDialogComponent,
        UniversoPopupComponent,
        UniversoDeleteDialogComponent,
        UniversoDeletePopupComponent,
    ],
    providers: [
        UniversoService,
        UniversoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MulticUniversoModule {}
