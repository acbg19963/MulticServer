import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MulticSharedModule } from '../../shared';
import {
    AyudaService,
    AyudaPopupService,
    AyudaComponent,
    AyudaDetailComponent,
    AyudaDialogComponent,
    AyudaPopupComponent,
    AyudaDeletePopupComponent,
    AyudaDeleteDialogComponent,
    ayudaRoute,
    ayudaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ayudaRoute,
    ...ayudaPopupRoute,
];

@NgModule({
    imports: [
        MulticSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AyudaComponent,
        AyudaDetailComponent,
        AyudaDialogComponent,
        AyudaDeleteDialogComponent,
        AyudaPopupComponent,
        AyudaDeletePopupComponent,
    ],
    entryComponents: [
        AyudaComponent,
        AyudaDialogComponent,
        AyudaPopupComponent,
        AyudaDeleteDialogComponent,
        AyudaDeletePopupComponent,
    ],
    providers: [
        AyudaService,
        AyudaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MulticAyudaModule {}
