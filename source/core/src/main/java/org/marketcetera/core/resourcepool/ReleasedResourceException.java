package org.marketcetera.core.resourcepool;

import org.marketcetera.core.MessageKey;

/**
 * Indicates an exception thrown when a {@link Resource} was returned to a {@link ResourcePool} and subsequently released.
 * 
 * @see {@link ResourcePool#returnResource(Resource)}
 *
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id: $
 */
public class ReleasedResourceException
        extends ResourcePoolException
{
    private static final long serialVersionUID = -4609487809796100582L;

    /**
     * Create a new ReleasedResourceException instance.
     */
    public ReleasedResourceException(String inMessage)
    {
        super(inMessage);
        // TODO Auto-generated constructor stub
    }

    /**
     * Create a new ReleasedResourceException instance.
     */
    public ReleasedResourceException(String inMsg,
            Throwable inNested)
    {
        super(inMsg,
              inNested);
        // TODO Auto-generated constructor stub
    }

    /**
     * Create a new ReleasedResourceException instance.
     */
    public ReleasedResourceException(Throwable inNested)
    {
        super(inNested);
        // TODO Auto-generated constructor stub
    }

    /**
     * Create a new ReleasedResourceException instance.
     */
    public ReleasedResourceException(MessageKey inKey)
    {
        super(inKey);
        // TODO Auto-generated constructor stub
    }

    /**
     * Create a new ReleasedResourceException instance.
     */
    public ReleasedResourceException(MessageKey inKey,
            Throwable inNested)
    {
        super(inKey,
              inNested);
        // TODO Auto-generated constructor stub
    }

}
