import { BaseEntity } from './../../shared';

export class Planeta implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public progreso?: number,
        public universo?: BaseEntity,
        public actividades?: BaseEntity[],
    ) {
    }
}
