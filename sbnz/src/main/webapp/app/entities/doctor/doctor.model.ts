import { BaseEntity, User } from './../../shared';

export class Doctor implements BaseEntity {
    constructor(
        public id?: number,
        public user?: User,
        public diagnoses?: BaseEntity[],
    ) {
    }
}
