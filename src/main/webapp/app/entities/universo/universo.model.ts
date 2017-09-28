import { BaseEntity } from './../../shared';

export class Universo implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public avatares?: BaseEntity[],
        public planetas?: BaseEntity[],
    ) {
    }
}
