package yte.intern.spring.application.managestudents.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import yte.intern.spring.application.managestudents.dto.GuestDTO;
import yte.intern.spring.application.managestudents.dto.GuestDTO.GuestDTOBuilder;
import yte.intern.spring.application.managestudents.entity.Guest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-10T13:43:45+0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 14.0.1 (AdoptOpenJDK)"
)
@Component
public class GuestMapperImpl implements GuestMapper {

    @Override
    public GuestDTO mapToDto(Guest guest) {
        if ( guest == null ) {
            return null;
        }

        GuestDTOBuilder guestDTO = GuestDTO.builder();

        guestDTO.name( guest.getName() );
        guestDTO.surname( guest.getSurname() );
        guestDTO.email( guest.getEmail() );
        guestDTO.tcKimlikNo( guest.getTcKimlikNo() );

        return guestDTO.build();
    }

    @Override
    public Guest mapToEntity(GuestDTO guestDTO) {
        if ( guestDTO == null ) {
            return null;
        }

        Guest guest = new Guest();

        guest.setName( guestDTO.getName() );
        guest.setSurname( guestDTO.getSurname() );
        guest.setTcKimlikNo( guestDTO.getTcKimlikNo() );
        guest.setEmail( guestDTO.getEmail() );

        return guest;
    }

    @Override
    public List<GuestDTO> mapToDto(List<Guest> guestList) {
        if ( guestList == null ) {
            return null;
        }

        List<GuestDTO> list = new ArrayList<GuestDTO>( guestList.size() );
        for ( Guest guest : guestList ) {
            list.add( mapToDto( guest ) );
        }

        return list;
    }

    @Override
    public List<Guest> mapToEntity(List<GuestDTO> guestDTOList) {
        if ( guestDTOList == null ) {
            return null;
        }

        List<Guest> list = new ArrayList<Guest>( guestDTOList.size() );
        for ( GuestDTO guestDTO : guestDTOList ) {
            list.add( mapToEntity( guestDTO ) );
        }

        return list;
    }
}
