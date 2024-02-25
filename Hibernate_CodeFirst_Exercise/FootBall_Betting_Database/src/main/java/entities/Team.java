package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team extends IdName{
    @Column
    private String logo;

    @Column(length = 3)
    private String initials;

    @ManyToOne
    @JoinColumn(name = "primary_kit",referencedColumnName = "id")
    private Color primaryKit;

    @ManyToOne
    @JoinColumn(name = "secondary_kit",referencedColumnName = "id")
    private Color secondaryKit;

    @ManyToOne
    @JoinColumn(name = "town_id")
    private Town town;

    public Team() {
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Color getPrimaryKit() {
        return primaryKit;
    }

    public void setPrimaryKit(Color primaryKit) {
        this.primaryKit = primaryKit;
    }

    public Color getSecondaryKit() {
        return secondaryKit;
    }

    public void setSecondaryKit(Color secondary) {
        this.secondaryKit = secondary;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
