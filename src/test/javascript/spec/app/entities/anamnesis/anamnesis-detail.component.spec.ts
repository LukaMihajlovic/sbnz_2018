/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SbnzTestModule } from '../../../test.module';
import { AnamnesisDetailComponent } from '../../../../../../main/webapp/app/entities/anamnesis/anamnesis-detail.component';
import { AnamnesisService } from '../../../../../../main/webapp/app/entities/anamnesis/anamnesis.service';
import { Anamnesis } from '../../../../../../main/webapp/app/entities/anamnesis/anamnesis.model';

describe('Component Tests', () => {

    describe('Anamnesis Management Detail Component', () => {
        let comp: AnamnesisDetailComponent;
        let fixture: ComponentFixture<AnamnesisDetailComponent>;
        let service: AnamnesisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [AnamnesisDetailComponent],
                providers: [
                    AnamnesisService
                ]
            })
            .overrideTemplate(AnamnesisDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnamnesisDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnamnesisService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Anamnesis(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.anamnesis).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
