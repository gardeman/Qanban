package se.qbranch.qanban

class PhaseEventUpdate extends Event implements Comparable{

    // TODO: Validera så att eventen inte sparas om inget värde har ändrats
    
    static constraints = {
        cardLimit ( nullable: true )
    }

    static transients = ['phase']
    Phase phase

    String name
    Integer cardLimit

    transient beforeInsert = {
        domainId = phase.domainId
    }

    transient afterInsert = {

        phase.name = name
        phase.cardLimit = cardLimit
        phase.save()

    }

    transient onLoad = {
        phase = Phase.findByDomainId(domainId)
    }

    int compareTo(Object o) {
        if (o instanceof Event) {
            Event event = (Event) o
            final int BEFORE = -1;
            final int EQUAL = 0;
            final int AFTER = 1;

            if(this.dateCreated < event.dateCreated) return AFTER
            if(this.dateCreated > event.dateCreated) return BEFORE

            return EQUAL
        }
    }

    boolean equals(Object o) {
        if(o instanceof Event) {
            Event event = (Event) o
            if(this.id == event.id)
            return true
        }
        return false
    }
}