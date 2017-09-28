import { BaseEntity } from './../../shared';

export class TipEstudiante implements BaseEntity {
    constructor(
        public id?: number,
        public tip?: string,
        public estudiante?: BaseEntity,
    ) {
    }
}
