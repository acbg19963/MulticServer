import { BaseEntity } from './../../shared';

export class ActividadxEstudiante implements BaseEntity {
    constructor(
        public id?: number,
        public acerto?: boolean,
        public cantayuda?: number,
        public tiempo?: number,
        public estudiante?: BaseEntity,
        public actividad?: BaseEntity,
    ) {
        this.acerto = false;
    }
}
