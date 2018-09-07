/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SbnzTestModule } from '../../../test.module';
import { AnamnesisDialogComponent } from '../../../../../../main/webapp/app/entities/anamnesis/anamnesis-dialog.component';
import { AnamnesisService } from '../../../../../../main/webapp/app/entities/anamnesis/anamnesis.service';
import { Anamnesis } from '../../../../../../main/webapp/app/entities/anamnesis/anamnesis.model';
import { DiagnosisService } from '../../../../../../main/webapp/app/entities/diagnosis';
import { IngredientService } from '../../../../../../main/webapp/app/entities/ingredient';
import { DrugService } from '../../../../../../main/webapp/app/entities/drug';

describe('Component Tests', () => {

    describe('Anamnesis Management Dialog Component', () => {
        let comp: AnamnesisDialogComponent;
        let fixture: ComponentFixture<AnamnesisDialogComponent>;
        let service: AnamnesisService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [AnamnesisDialogComponent],
                providers: [
                    DiagnosisService,
                    IngredientService,
                    DrugService,
                    AnamnesisService
                ]
            })
            .overrideTemplate(AnamnesisDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnamnesisDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnamnesisService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Anamnesis(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.anamnesis = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'anamnesisListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Anamnesis();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.anamnesis = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'anamnesisListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
