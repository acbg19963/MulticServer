import { BaseEntity } from './../../shared';

export class Ayuda implements BaseEntity {
    constructor(
        public id?: number,
        public enunciado?: string,
        public actividad?: BaseEntity,
    ) {
    }
}
