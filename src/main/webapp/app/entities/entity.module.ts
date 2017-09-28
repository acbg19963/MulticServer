import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MulticEstudianteModule } from './estudiante/estudiante.module';
import { MulticAvatarModule } from './avatar/avatar.module';
import { MulticUniversoModule } from './universo/universo.module';
import { MulticPlanetaModule } from './planeta/planeta.module';
import { MulticActividadModule } from './actividad/actividad.module';
import { MulticRespuestaModule } from './respuesta/respuesta.module';
import { MulticCursoModule } from './curso/curso.module';
import { MulticAyudaModule } from './ayuda/ayuda.module';
import { MulticProfesorModule } from './profesor/profesor.module';
import { MulticTipEstudianteModule } from './tip-estudiante/tip-estudiante.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MulticEstudianteModule,
        MulticAvatarModule,
        MulticUniversoModule,
        MulticPlanetaModule,
        MulticActividadModule,
        MulticRespuestaModule,
        MulticCursoModule,
        MulticAyudaModule,
        MulticProfesorModule,
        MulticTipEstudianteModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MulticEntityModule {}
