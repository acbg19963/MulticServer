import { BaseEntity } from './../../shared';

export class Curso implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public profesores?: BaseEntity[],
        public estudiantes?: BaseEntity[],
    ) {
    }
}
