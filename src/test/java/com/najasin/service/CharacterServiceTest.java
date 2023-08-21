package com.najasin.service;

import com.najasin.domain.character.service.CharacterService;
import com.najasin.domain.character.entity.Body;
import com.najasin.domain.character.repository.BodyRepository;
import com.najasin.domain.character.entity.CharacterSet;
import com.najasin.domain.character.repository.CharacterSetRepository;
import com.najasin.domain.character.dto.AllCharacterItems;
import com.najasin.domain.character.entity.Expression;
import com.najasin.domain.character.repository.ExpressionRepository;
import com.najasin.domain.character.entity.Face;
import com.najasin.domain.character.repository.FaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {
    @InjectMocks
    private CharacterService characterService;
    @Mock
    private FaceRepository faceRepository;

    @Mock
    private BodyRepository bodyRepository;

    @Mock
    private ExpressionRepository expressionRepository;

    @Mock
    private CharacterSetRepository characterSetRepository;

    @Test
    public void testGetAllCharacterItems() {
        //given
        List<Face> mockFaces = new ArrayList<>();
        mockFaces.add(new Face(1L, "Face 1 Show URL", "Face 1 Layout URL", "URL"));
        mockFaces.add(new Face(2L, "Face 2 Show URL", "Face 2 Layout URL","URL"));

        List<Body> mockBodies = new ArrayList<>();
        mockBodies.add(new Body(1L, "Body 1 Show URL", "Body 1 Layout URL","URL"));
        mockBodies.add(new Body(2L, "Body 2 Show URL", "Body 2 Layout URL","URL"));

        List<Expression> mockExpressions = new ArrayList<>();
        mockExpressions.add(new Expression(1L, "Expression 1 Show URL", "Expression 1 Layout URL","URL"));
        mockExpressions.add(new Expression(2L, "Expression 2 Show URL", "Expression 2 Layout URL","URL"));

        List<CharacterSet> mockCharacterSets = new ArrayList<>();
        mockCharacterSets.add(new CharacterSet(1L, "Character Set 1 URL","URL"));

        when(faceRepository.findAll()).thenReturn(mockFaces);
        when(bodyRepository.findAll()).thenReturn(mockBodies);
        when(expressionRepository.findAll()).thenReturn(mockExpressions);
        when(characterSetRepository.findAll()).thenReturn(mockCharacterSets);

        // Call the service method
        AllCharacterItems allCharacterItems = characterService.getAllCharacterItems();

        // Assertions
        assertEquals("기본 이미지 url", allCharacterItems.getBaseImage());

        assertEquals(2, allCharacterItems.getCharacterItems().getFace().size());
        assertEquals(2, allCharacterItems.getCharacterItems().getBody().size());
        assertEquals(2, allCharacterItems.getCharacterItems().getExpression().size());
        assertEquals(1, allCharacterItems.getCharacterItems().getSet().size());

        // Verify that the methods were called as expected
        verify(faceRepository, times(1)).findAll();
        verify(bodyRepository, times(1)).findAll();
        verify(expressionRepository, times(1)).findAll();
        verify(characterSetRepository, times(1)).findAll();
    }
}
